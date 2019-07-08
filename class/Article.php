<?php

require_once __DIR__.'/Err.php';
class Article
{
    public function __construct(PDO $_db)
    {
        $this->_db = $_db;
    }

    public function add($title,$content,$user_id)
    {
        if (empty($title)) {
            throw new Exception("文章标题不能为空", Err::ARTICLE_TITLE_CANNOT_NULL);
        }
        if (empty($content)) {
            throw new Exception("文章标题不能为空", Err::ARTICLE_CONTENT_CANNOT_NULL);
        }
        $sql = "insert into `article` (`title`, `content`, `user_id`) values(:title,:content,:user_id)";
        $sm = $this->_db->prepare($sql);

        $sm->bindParam('title', $title);
        $sm->bindParam('content', $content);
        $sm->bindParam('user_id', $user_id);

        if (!$sm->execute()) {
            throw new Exception("文章发布失败!", Err::ARTICLE_ADD_FAIL);
        }

        return [
            'title' => $title,
            'content' => $content,
        ];
    }

    public function del($arid, $user_id)
    {
        $article = $this->view($arid);
        if ($user_id != (int)$article['user_id']) {
            throw new Exception("权限不足", Err::PERMITION_NOT_ALLOW);
        }

        $sql = "delete from `article` where `arid`=:arid";
        $sm = $this->_db->prepare($sql);
        $sm->bindParam('arid', $arid);
        if (!$sm->execute()) {
            throw new Exception("文章删除失败", Err::ARTICLE_DELETE_FAIL);
        }

        return true;
    }

    // 获取一篇文章
    public function view($arid)
    {
        if (empty($arid)) {
            throw new Exception("文章id不能为空",Err::ATICLE_ID_CANNOT_NULL);
        }
        $sql = "select * from `article` where `arid`=:id";
        $sm = $this->_db->prepare($sql);
        $sm->bindParam('id',$arid);
        if (!$sm->execute()) {
            throw new Exception("获取文章失败",Err::ARTICLE_GET_FAIL);
        }

        $article = $sm->fetch(PDO::FETCH_ASSOC);
        if (empty($article)) {
            throw new Exception('文章不存在！',Err::ARTICLE_NOT_EXISTS);
        }

        return $article;
    }

    // 获取文章列表
    public function lst($start, $num)
    {
        $sql = "select * from `article` limit :start,:num";
        $sm = $this->_db->prepare($sql);
        $sm->bindParam('start', $start);
        $sm->bindParam('num', $num);
        if (!$sm->execute()) {
            throw new Exception("获取文章失败",Err::ARTICLE_GET_FAIL);
        }

        $article = $sm->fetchAll(PDO::FETCH_ASSOC);
        if (empty($article)) {
            throw new Exception('文章不存在！',Err::ARTICLE_NOT_EXISTS);
        }

        return $article;
    }
    
    // 编辑文章
    public function edit($arid, $title, $content, $user_id)
    {
        $article = $this->view($arid);
        if ($user_id !== (int)$article['user_id']) {
            throw new Exception('权限不足！',Err::PERMITION_NOT_ALLOW);
        }

        $title = empty($title)?$article['title']:$title;
        $content = empty($content)?$article['content']:$content;

        if ($article['title'] == $title && $article['content'] = $content) {
            return $article;
        }
        $sql = "update `article` set `title`=:title,`content`=:content where `arid`=:arid";
        $sm = $this->_db->prepare($sql);
        $sm->bindParam('title', $title);
        $sm->bindParam('content', $content);
        $sm->bindParam('arid', $arid);

        if (!$sm->execute()) {
            throw new Exception('文章修改失败', Err::ARTICLE_EDIT_FAIL);
        }

        return [
            'title' => $title,
            'content' => $content,
        ];
    }
}