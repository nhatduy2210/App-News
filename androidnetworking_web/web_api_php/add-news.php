<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: PUT");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//http://127.0.0.1:8686/add-news.php
//import file connection.php


include_once './connection.php';

//đọc dữ liệu từ json
$data = json_decode(file_get_contents("php://input"));
//đọc dư liệu từ json
$title = $data->title;
$content = $data->content;
$image = $data ->image;
$user_id = $data->user_id;
$topic_id = $data->topic_id;

//thêm dữ liệu vào database
$sqlQuery ="INSERT INTO news(title, content,image, user_id,topic_id,created_at) VALUES
('$title','$content','$image','$user_id','$topic_id',now())";
//thưc thi câu lệnh
$stmt = $dbConn -> prepare($sqlQuery);
$stmt->execute();
//trả về thông báo
echo json_encode(array('message'=>'thêm mới tin tức thành công'));

?>