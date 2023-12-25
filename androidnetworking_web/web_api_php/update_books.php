<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: PUT");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Import file connection.php
include_once './connection.php';

// Lấy id từ query string
$id = isset($_GET['id']) ? $_GET['id'] : $_GET('Không tìm thấy ID');
// Kiểm tra id có phải là số dương không
if (!is_numeric($id) || $id <= 0) {
  
    echo json_encode(array("message" => "Lỗi: ID không hợp lệ."));
    exit();
}
// Đọc dữ liệu từ body của request
$data = json_decode(file_get_contents("php://input"));

// Kiểm tra giá trị giá sách
if (!isset($data->price)  || !is_numeric($data->price) || !filter_var($data->price, FILTER_VALIDATE_FLOAT)  || $data->price <= 0 || $data->price < 100 || $data->price > 10000) {
   
    echo json_encode(array("message" => "Lỗi: Sai thông tin cập nhật."));
    exit();
}



// Kiểm tra xem ID có tồn tại trong cơ sở dữ liệu không
$sqlCheckId = "SELECT id FROM books WHERE id = :id";
$stmtCheckId = $dbConn->prepare($sqlCheckId);
$stmtCheckId->bindParam(':id', $id);
$stmtCheckId->execute();
if ($stmtCheckId->rowCount() == 0) {
   
    echo json_encode(array("message" => "Lỗi: ID không tồn tại."));
    exit();
}

try{
// Lấy giá mới từ dữ liệu
$newPrice = $data->price;

// Thực hiện truy vấn để cập nhật giá sách
$sql = "UPDATE books SET price = :price WHERE id = :id";

// Chuẩn bị và thực thi câu lệnh
$stmt = $dbConn->prepare($sql);
$stmt->bindParam(':price', $newPrice);
$stmt->bindParam(':id', $id);

if ($stmt->execute()) {
    
  
    echo json_encode(array("message" => "Cập nhật thành công."));
} else {
  
   
    echo json_encode(array("message" => "Cập nhật thất bại."));
}
}catch (Exception $e) {
    echo 'Caught exception: ', $e->getMessage(), "\n";}
?>
