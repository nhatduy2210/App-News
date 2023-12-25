<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


// Import file connection.php
include_once './connection.php';

// Đọc dữ liệu từ body 
$data = json_decode(file_get_contents("php://input"));

try{
// Lấy thể loại và tác giả
$genre = $data->genre;
$author = $data->author;


$sqlQuery = "SELECT id, title, price, genre, author, published FROM books WHERE genre = :genre AND author = :author ORDER BY price DESC";

// Chuẩn bị và thực thi câu lệnh
$stmt = $dbConn->prepare($sqlQuery);
$stmt->bindParam(':genre', $genre);
$stmt->bindParam(':author', $author);
$stmt->execute();

// Lấy dữ liệu từ PDO
$books = $stmt->fetchAll(PDO::FETCH_ASSOC);

// Chuyển đổi dữ liệu sang JSON và in ra response
echo json_encode($books);
}catch (Exception $e) {
    echo 'Caught exception: ', $e->getMessage(), "\n";}
?>
