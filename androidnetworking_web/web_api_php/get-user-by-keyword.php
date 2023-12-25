<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//http://127.0.0.1:8686/get-news-by-keywword.php
//import file connection.php

$keyWord = $_GET['keyWord'];
include_once './connection.php';

//đọc dữ liệu từ database

$sqlQuery = "SELECT id, email, name,  avatar
FROM users WHERE name LIKE '%$keyWord%' 
OR email LIKE '%$keyWord%'";

//thưc thi câu lệnh PDO
$stmt = $dbConn -> prepare($sqlQuery);
$stmt->execute();
//lấy tất cả dữ liệu của câu lệnh
$news = $stmt->fetchAll(PDO::FETCH_ASSOC);
//trả về dữ liệu dạng json
echo json_encode($news);

?>