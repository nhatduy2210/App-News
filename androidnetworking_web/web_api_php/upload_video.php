<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

try {
    $currentDirectory = getcwd();
    $uploadDirectory = "/uploads/";

    $fileType = $_FILES['file']['type'];
    $allowedTypes = array('video/mp4', 'video/mpeg', 'video/quicktime'); // Thêm các kiểu video được chấp nhận

    if (!in_array($fileType, $allowedTypes)) {
        throw new Exception("Invalid file type. Only MP4, MPEG, and QuickTime videos are allowed.");
    }

    $fileName = $_FILES['file']['name'];
    $fileTmpName = $_FILES['file']['tmp_name'];
    $uploadPath = $currentDirectory . $uploadDirectory . basename($fileName);

    // Upload file
    move_uploaded_file($fileTmpName, $uploadPath);

    echo json_encode(
        array(
            "error" => false,
            "message" => "Upload successful",
            "path" => "http://172.16.122.209:8686/uploads/" . $fileName
        )
    );
} catch (Exception $e) {
    echo json_encode(
        array(
            "error" => true,
            "message" => "Upload failed: " . $e->getMessage(),
            "path" => null
        )
    );
}
?>
