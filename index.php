<?php

$db = require_once __DIR__.'/lib/db.php';
$db->setAttribute(PDO::ATTR_EMULATE_PREPARES,false);
require_once __DIR__.'/class/User.php';
require_once __DIR__.'/class/Article.php';
require_once __DIR__.'/class/Rest.php';

// 用户模块测试
$user = new User($db);
// $user->register('adm','123456');
// var_dump($user->login('adm','123456'));

// 文章模块测试
$ar = new Article($db);
// var_dump($ar->add('test','hello','5'));
// var_dump($ar->view(1));
// var_dump($ar->edit(1,'标题1','content',5));
// var_dump($ar->del(1,5));

$api = new Rest($user,$ar);
$api->run();