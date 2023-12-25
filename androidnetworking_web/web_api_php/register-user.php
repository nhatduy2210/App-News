<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST ");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Import connection.php
include_once './connection.php';

try {
    // Đọc dữ liệu từ request
    $data = json_decode(file_get_contents("php://input"));

    // Kiểm tra xem các trường cần thiết đã được cung cấp chưa
    if (!isset($data->name) || !isset($data->password) || !isset($data->email)) {
        throw new Exception("Bạn chưa cung cấp đầy đủ.");
    }

    // Kiểm tra xem tên người dùng đã tồn tại chưa
    $checkUserQuery = "SELECT * FROM users WHERE email = :email  AND name = :name";
    $checkUserStmt = $dbConn->prepare($checkUserQuery);
    $checkUserStmt->bindParam(':name', $data->name);
    $checkUserStmt->bindParam(':email', $data->email);
    $checkUserStmt->execute();

    if ($checkUserStmt->rowCount() > 0) {
        throw new Exception("Tên đăng nhập hoặc email đã tồn tại.");
    }

    // Nếu role và avatar không được cung cấp, đặt giá trị mặc định
    $role = isset($data->role) ? $data->role : 'user';
    $avatar = isset($data->avatar) ? $data->avatar : 'trống';

    // Thêm người dùng mới vào cơ sở dữ liệu
    $insertUserQuery = "INSERT INTO users (email, password, name, role, avatar) VALUES (:email, :password, :name, :role, :avatar)";
    $insertUserStmt = $dbConn->prepare($insertUserQuery);

    // Băm mật khẩu trước khi lưu vào cơ sở dữ liệu (nên sử dụng phương pháp băm mật khẩu an toàn)
    //$hashedPassword = password_hash($data->password, PASSWORD_DEFAULT);

    $insertUserStmt->bindParam(':name', $data->name);
    //$insertUserStmt->bindParam(':password', $hashedPassword);
    $insertUserStmt->bindParam(':password', $data->password);
    $insertUserStmt->bindParam(':email', $data->email);
    $insertUserStmt->bindParam(':avatar', $avatar);
    $insertUserStmt->bindParam(':role', $role);

    $insertUserStmt->execute();

    // Trả về thông báo thành công
    echo json_encode(array('message' => 'User registered successfully.'));
} catch (Exception $e) {
    echo json_encode(array("message" => $e->getMessage()));
}
?>
