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
$id = $data->id;
$name = $data->name;
$description = $data ->description;


//thêm dữ liệu vào database
$sqlQuery ="INSERT INTO topics(id,name,description) VALUES
('$id','$name','$description')";
//thưc thi câu lệnh
$stmt = $dbConn -> prepare($sqlQuery);
$stmt->execute();
//trả về thông báo
echo json_encode(array('message'=>'thêm mới topic thành công'));

?>