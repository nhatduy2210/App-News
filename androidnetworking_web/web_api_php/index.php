<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST GET PUT DELETE");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
//đọc dữ liệu từ phương thức get của query string
//http://127.0.0.1:8686/index.php?name=abc&age=20
$name= $_GET['name'];
$age = $_GET['age'];
//trả về dữ liệu dạng json
echo json_encode(
array(
    "message" => "Xin chào bạn $name, bạn $age tuổi",
    "name" => $name,
    "age" => $age 
)
);
?>
