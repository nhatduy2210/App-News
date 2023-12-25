<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST ");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
// http://127.0.0.1:8686/login.php
// đăng nhập tài khoản
// import connection.php
include_once './connection.php';
include_once './helpers/jwt.php';
try {
    // đọc dữ liệu từ json
    $data = json_decode(file_get_contents("php://input"));
    // đọc dữ liệu từ json
    $email = $data->email;
    $password = $data->password;
    // kiểm tra trong db
    $sqlQuery = "SELECT * FROM users WHERE 
                            email = '$email' AND password = '$password'";
    // thực thi câu lệnh pdo
    $stmt = $dbConn->prepare($sqlQuery);
    $stmt->execute();
    // lấy dữ liệu từ db
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
    // kiểm tra dữ liệu
    if ($user) {

        //nếu dùng mã băm,cắt query passwword
        //$check = password_verify($password,$user['password']);
        //if($check == true)

        //tạo token
        $headers = array('alg' => 'HS256','type' => 'JWT');
        $payload = array(
            'id' => $user['ID'],// thêm id vào payload để sử dụng cho việc update thông tin user
            'email' => $user['EMAIL'],
            'role' => $user['ROLE'],
            'exp' => (time()+360)
        );
        $token = generate_jwt($headers,$payload);


        echo json_encode(
            array(
                "status" => true,
                "user" =>$user,
                "token" =>$token
            )
        );
    } else {
        // trả về json
        echo json_encode(array(
            "status" => false,
            "user"=> null,
            'message' => 'Đăng nhập thất bại'));
    }
} catch (Exception $e) {
    //throw $th;
    echo json_encode(array("message" => $e->getMessage()));}


    ?>