<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");



// http://127.0.0.1:8686/check-reset-password.php
// quên mật khẩu
// import connection.php
include_once './connection.php';
try {
    // đọc email từ body
    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;
    $token = $data->token;

    //kiểm tra email và token có trong db không
    $query = "select * from password_resets where email = '$email'and token = '$token' 
    and created_at >= now() - interval 1 hour 
    and available = 1";
    
    $stmt = $dbConn->prepare($query);
$stmt->execute();
// lấy dữ liệu từ pdo
$result = $stmt->fetch(PDO::FETCH_ASSOC);
if($result){
    echo json_encode(array(
        "status" => true,
        "message" => "token is valid"
    ));
} else{
    echo json_encode(array(
        "status" => false,
        "message" => "Token is invalid"
    ));
}

    
} catch (\Throwable $th) {
    echo json_encode(array(
        "status" => false,
        "message" => "co loi ne"
    ));
}