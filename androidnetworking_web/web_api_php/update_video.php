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
   
   
    $IMAGE = $data->IMAGE;
   
    $ID = $_GET['ID'];

    // Cập nhật dữ liệu vào database
    $sqlQuery = "UPDATE video SET IMAGE = :IMAGE WHERE ID = :ID";

    // Thực thi câu lệnh
    $stmt = $dbConn->prepare($sqlQuery);

    // Bind giá trị
   
    $stmt->bindParam(':IMAGE', $IMAGE);
    
    $stmt->bindParam(':ID', $ID);

    // Thực thi câu lệnh
    if ($stmt->execute()) {
        echo json_encode(array('message' => 'Cập nhật tin tức thành công'));
    } else {
        echo json_encode(array('message' => 'Cập nhật tin tức thất bại'));
    }
} catch (Exception $e) {
    echo json_encode(array('message' => $e->getMessage()));
}
?>
