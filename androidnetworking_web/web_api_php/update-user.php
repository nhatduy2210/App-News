<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: PUT");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Import file connection.php
include_once './connection.php';

try {
    $data = json_decode(file_get_contents("php://input"));
    // Đọc dữ liệu từ JSON
    $name = $data->name;
    $avatar = $data->avatar;
    $email = $data->email;
    $id = $_GET['id'];

    // Cập nhật dữ liệu vào database
    $sqlQuery = "UPDATE users SET name = :name, avatar = :avatar,email = :email WHERE id = :id";

    // Thực thi câu lệnh
    $stmt = $dbConn->prepare($sqlQuery);

    // Bind giá trị
    $stmt->bindParam(':name', $name);
    $stmt->bindParam(':avatar', $avatar);
    $stmt->bindParam(':email', $email);
  
    $stmt->bindParam(':id', $id);

    // Thực thi câu lệnh
    if ($stmt->execute()) {
        echo json_encode(array('message' => 'Cập nhật user thành công'));
    } else {
        echo json_encode(array('message' => 'Cập nhật user thất bại'));
    }
} catch (Exception $e) {
    echo json_encode(array('message' => $e->getMessage()));
}
?>
