<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


// http://127.0.0.1:8686/check-reset-password.php
//đọc email và token từ body
// check reset mật khẩu
// import connection.php
include_once './connection.php';
try {
    // đọc email từ body
    //check email token còn dùng được k
    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;
    $token = $data->token;
    
    $query = "SELECT * FROM PASSWORD_RESETS WHERE email = '$email' AND token = '$token'
    AND created_at >= now() - interval 1 hour 
    AND available = 1";
       $stmt = $dbConn->prepare($query);
       $stmt->execute();
       $result = $stmt->fetch(PDO::FETCH_ASSOC);


       if($result){
        echo json_encode(array(
            "status" =>true,
            "message" =>"Token is valid"
        ));
       }else{
        echo json_encode(array(
            "status" =>false,
            "message" =>"Token is invalid"
        ));
       }

     }

catch (\Throwable $th) {
    echo json_encode(array(
        "status" => false,
        "message" => $th -> getMessage()
    ));
}

?>