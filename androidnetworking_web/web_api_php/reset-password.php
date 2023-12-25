<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once './connection.php';


try {
    // đọc email ,token,pass,pass_confimm từ body
    //check email token còn dùng được k
    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;
    $token = $data->token;
    $password = $data->password;
    $password_confirm = $data->password_confirm;

    //kiểm tra pass và cònirmpass
    if($password != $password_confirm){
        echo json_encode(array(
            "status" => false,
            "message" => "không trùngg khớp"
        ));  
        return;
    }
    //kiểm tra email và token có trong db không
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
        //cập nhật mật khẩu mới vào bảng users
        $query = "update users set password ='$password' where email = '$email' ";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
                //cập nhật available = 0 trong passwword-resets
        $query = "update PASSWORD_RESETS set available = 0 where email = '$email' ";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();


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