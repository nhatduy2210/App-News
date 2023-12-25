<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods:DELETE");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//http://127.0.0.1:8686/delete-news.php?id=
//xoá 1 bảng tin theo id
//import file connection.php


include_once './connection.php';

try{
    //lấy dữ liệu từ json
    $id = $_GET['id'];






    //thưc thi câu lệnh

    $sqlQuery = "DELETE FROM users WHERE id=$id";


$stmt = $dbConn -> prepare($sqlQuery);
$stmt->execute();
//trả về thông báo
if ($stmt->execute()) {
    echo json_encode(array('message' => 'Xóa tải khoản thành công'));
} else {
    echo json_encode(array('message' => 'Xóa tài khoản thất bại'));
}
} catch (Exception $e) {
    echo 'Caught exception: ', $e->getMessage(), "\n";
}

?>