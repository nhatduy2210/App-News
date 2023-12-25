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
    $title = $data->title;
    $content = $data->content;
    $image = $data->image;
    $user_id = $data->user_id;
    $topic_id = $data->topic_id;
    $id = $_GET['id'];

    // Cập nhật dữ liệu vào database
    $sqlQuery = "UPDATE news SET title = :title, content = :content, image = :image, user_id = :user_id, topic_id = :topic_id WHERE id = :id";

    // Thực thi câu lệnh
    $stmt = $dbConn->prepare($sqlQuery);

    // Bind giá trị
    $stmt->bindParam(':title', $title);
    $stmt->bindParam(':content', $content);
    $stmt->bindParam(':image', $image);
    $stmt->bindParam(':user_id', $user_id);
    $stmt->bindParam(':topic_id', $topic_id);
    $stmt->bindParam(':id', $id);

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
