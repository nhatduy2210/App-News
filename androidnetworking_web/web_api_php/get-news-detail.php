<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods:GET");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//http://127.0.0.1:8686/get-news-detail.php?id=1
//import file connection.php
include_once './connection.php';
//đọc id từ query string
$id= $_GET['id'];
//đọc dữ liệu từ database
$sqlQuery = "SELECT id, title, content,  user_id,topic_id,created_at,image
FROM news WHERE id = $id";

//thưc thi câu lệnh
$stmt = $dbConn -> prepare($sqlQuery);
$stmt->execute();
//lấy tất cả dữ liệu của câu lệnh
$news = $stmt->fetch(PDO::FETCH_ASSOC);
//trả về dữ liệu dạng json
echo json_encode($news);

?>