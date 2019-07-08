<?php
require_once __DIR__.'/Err.php';

class User
{
    // 初始化PDO数据库连接
    private $_db;
    public function __construct(PDO $_db)
    {
        $this->_db = $_db;
    }

    // 用户注册
    public function register($username,$password)
    {
        if (empty($username)) {
            throw new Exception("用户名不能为空(@_*)", Err::USERNAME_CANNOT_NULL);
        }

        if (empty($password)) {
            throw new Exception("用户密码不能为空(@_*)", Err::USERPASS_CANNOT_NULL);
        }

        if($this->_isUsernameExists($username)) {
            throw new Exception("用户名已注册", Err::USERNAME_EXISTS);
        }

        $sql = "insert into `user`(`username`,`password`,`salt`) values(:username,:password,:salt)";
        $sm = $this->_db->prepare($sql);
        $sm->bindParam('username', $username);
        $salt = md5(mcrypt_create_iv(32));
        $password = $this->_md5($salt,$password);
        $sm->bindParam('password', $password);
        $sm->bindParam('salt', $salt);
        if (!$sm->execute()) {
            throw new Exception("注册失败", Err::REGESTER_FAIL);
        }

        return [
            'username' => $username,
            'user_id' => $this->_db->lastInsertId(),
        ];
    }

    // 用户登录
    public function login($username,$password)
    {
        if (empty($username)) {
            throw new Exception("用户名不能为空(@_*)", Err::USERNAME_CANNOT_NULL);
        }

        if (empty($password)) {
            throw new Exception("用户密码不能为空(@_*)", USERPASS_CANNOT_NULL);
        }

        $preSql = "select `salt` from `user` where `username`=:username";
        $preSm = $this->_db->prepare($preSql);
        $preSm->bindParam('username', $username);
        $preSm->execute();
        $salt = $preSm->fetch(PDO::FETCH_ASSOC);
        $salt = $salt['salt'];
        $sql = "select * from `user` where `username`=:username and `password`=:password";
        $sm = $this->_db->prepare($sql);
        $password = $password.$salt;
        $password = md5($password);
        $sm->bindParam('username', $username);
        $sm->bindParam('password', $password);

        if (!$sm->execute()) {
            throw new Exception("登录失败", Err::LOGIN_FAIL);
        }

        $re = $sm->fetch(PDO::FETCH_ASSOC);
        if (!$re) {
            throw new Exception("用户名或密码错误", Err::USERNAME_OR_PASSWORD_ERR);
        }

        return $re;
    }
    // 判断用户名是否已经存在
    private function _isUsernameExists($username)
    {
        $sql = "select * from `user` where `name`=username";
        $sm = $this->_db->prepare($sql);
        $sm->bindParam('username',$username);

        $sm->execute();
        $re = $sm->fetch(PDO::FETCH_ASSOC);
        return !empty($re);
    }

    // 给用户密码加盐
    private function _md5($salt,$password)
    {
        $password = $password.$salt;
        return md5($password);
    }
}