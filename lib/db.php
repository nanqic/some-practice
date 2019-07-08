<?php

// 引入数据库配置
require_once __DIR__."/conf.php";
// PDO连接数据库
return new PDO('mysql:host='.HOST.';dbname='.DBNAME,DBUSER,DBPASS);