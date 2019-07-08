<?php

class Rest
{
    // 用户模块
    private $_user;
    // 文章模块
    private $_article;

    // 构造方法
    public function __construct($_user, $_article)
    {
        $this->_user = $_user;
        $this->_article = $_article;
    }

    // 请求方法
    private $_requestMethod;
    // 允许请求的方法
    private $_allowMethod = ['GET','POST','PUT','DELETE'];
    //请求资源
    private $_requestResource;
    // 允许请求的资源
    private $_allowResource = ['user','article'];
    // 请求状态码
    private $_statusCode = [
        '200' => 'OK',
        '400' => 'Resource Error',
        '403' => 'Login Error',
        '404' => 'NOT FOUND',
        '405' => 'Method Not Allowed',
        '500' => 'SERVER ERROR'
    ];
    // 版本号
    private $_version;
    // 资源标识
    private $_requestUrl;
    // api启动方法
    public function run()
    {
        try {
            $this->setMethod();
            $this->setResource();
            if ($this->_requestResource == 'user') {
                $this->sendUser();
            } else {
                $this->sendArticle();
            }
        } catch (Exception $e) {
            $this->_json($e->getMessage(), $e->getCode());
        }
        
    }

    // 处理用户请求
    private function sendUser()
    {
        if ($this->_requestMethod != 'POST') {
            throw new Exception("请求方法必须为POST", 405);
        }

        if (empty($this->_requestUrl)) {
            throw new Exception("请求参数缺失", 400);
        }

        if ($this->_requestUrl == 'register') {
            $this->doregister();
        } elseif ($this->_requestUrl == 'login') {
            $this->dologin();
        } else {
            
            throw new Exception("请求资源不被允许", 405);
        }
    }

    // 用户注册接口
    private function doregister()
    {
        $data = $this->getBody();
        if (empty($data['username'])) {
            throw new Exception("用户名不能为空", 400);
        }

        if (empty($data['password'])) {
            throw new Exception("用户密码不能为空", 400);
        }
        $user = $this->_user->register($data['username'], $data['password']);
        if ($user) {
            $this->_json('注册成功!',200);
        }
    }

    // 用户登录接口
    private function dologin()
    {
        $data = $this->getBody();
        if (empty($data['username'])) {
            throw new Exception("用户名不能为空", 400);
        }

        if (empty($data['password'])) {
            throw new Exception("用户密码不能为空", 400);
        }
        $user = $this->_user->login($data['username'], $data['password']);
        $data = [
            'data' => [
                'user_id' => $user['id'],
                'username' => $user['username'],
                'token' => 1,
            ],
            'message' => '登录成功!',
            'code' => 200,
        ];
        echo json_encode($data);
    }

    private function getBody()
    {
        $data = file_get_contents("php://input");
        if (empty($data)) {
            throw new Exception("请求参数缺失", 400);
        }
        return json_decode($data, true);
    }

    // 处理文章请求
    private function sendArticle()
    {
        if (empty($this->_requestUrl)) {
            throw new Exception("请求参数缺失", 400);
        }

        switch ($this->_requestUrl) {
            case 'view':
                $this->doview();
                break;
            case 'add':
                $this->doadd();
                break;
            case 'edit':
                $this->doedit();
                break;
            case 'del':
                $this->dodel();
                break;
            case 'lst':
                $this->dolst();
                break;
            default:
            throw new Exception("请求资源不被允许", 405);
        }
    }

    // 获取一篇文章的api
    private function doview()
    {
        $data = $this->getBody();
        if (empty($data['arid'])) {
            throw new Exception("文章id不能为空", 400);
        }
        $ar = $this->_article->view($data['arid']);
        if ($ar) {
            $this->_json('OK',200);
            echo json_encode($ar);
        }
    }

    // 获取文章列表的api

    private function dolst()
    {
        $data = $this->getBody();
        if (empty($data)) {
            throw new Exception("请输入查询起点和数量", 400);
        }
        $ar = $this->_article->lst($data['start'], $data['num']);
        if ($ar) {
            $this->_json('OK',200);
            echo json_encode($ar);
        }
    }

     // 添加一篇文章的api
     private function doadd()
     {
         $data = $this->getBody();
         if (empty($data['title'])) {
             throw new Exception("文章标题不能为空", 400);
         }
         if (empty($data['content'])) {
            throw new Exception("文章内容不能为空", 400);
        }
         $ar = $this->_article->add($data['title'], $data['content'], $data['user_id']);
         if ($ar) {
             $this->_json('OK',200);
             echo json_encode($ar);
         }
     }

      // 编辑文章的api
    private function doedit()
    {
        $data = $this->getBody();
        /*
        if (!$this->isLogin($data['token'])) {
            throw new Exception("请重新登录", 403);
        }*/
        if (empty($data['arid'])) {
            throw new Exception("文章id不能为空", 400);
        }
        $article = $this->_article->view($data['arid']);
        if ($article['user_id'] != 3/* $_SESSION['userInfo']['id] */) {
            throw new Exception("请重新登录", 403);
        }
        $ar = $this->_article->edit($data['arid'], $data['title'], $data['content'], $data['user_id']);
        if ($ar) {
            $this->_json('OK',200);
            echo json_encode($ar);
        }
    }

    // 删除一篇文章的api
    private function dodel()
    {
        $data = $this->getBody();
        if (empty($data['arid'])) {
            throw new Exception("文章id不能为空", 400);
        }
        if (empty($data['user_id'])) {
            throw new Exception("用户id不能为空", 400);
        }

        $ar = $this->_article->del($data['arid'],$data['user_id']);
        if ($ar) {
            $this->_json('OK',200);
            echo '文章删除成功!';
        }
    }

    // 设置api启动方法
    private function setMethod()
    {
        $this->_requestMethod = $_SERVER['REQUEST_METHOD'];
        if (!in_array($this->_requestMethod, $this->_allowMethod)) {
            throw new Exception("请求方法不被允许", 405);
        }
    }

    // 处理资源
    private function setResource()
    {
        $path = $_SERVER['PATH_INFO'];
        $params = explode('/',$path);
        var_dump($params);
        $this->_requestResource = $params['2'];
        if (!in_array($this->_requestResource, $this->_allowResource)) {
            throw new Exception('请求资源不被允许',405);
        }
        $this->_version = $params[1];

        if (!empty($params[3])) {
            $this->_requestUrl = $params[3];
        }
    }

    // 返回客户端状态码
    private function _json($message, $code)
    {
        if ($code !== 200 && $code > 200) {

            header('HTTP/1.1' .$code.' '. $this->_statusCode[$code]);
        }
        header("Content-type:application/json;charset=utf8");
        if (!empty($message)) {

            echo json_encode(['message' => $message, 'code' => $code]);
        }
    }
}