<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


use PHPMailer\PHPMailer\PHPMailer;

include_once $_SERVER['DOCUMENT_ROOT'] . '/helpers/PHPMailer-master/PHPMailer-master/src/PHPMailer.php';
include_once $_SERVER['DOCUMENT_ROOT'] . '/helpers/PHPMailer-master/PHPMailer-master/src/SMTP.php';
include_once $_SERVER['DOCUMENT_ROOT'] . '/helpers/PHPMailer-master/PHPMailer-master/src/Exception.php';


// http://127.0.0.1:8686/forgot-password.php
// quên mật khẩu
// import connection.php
include_once './connection.php';
try {
    // đọc email từ body
    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;

    // kiểm tra email có trong db hay không
    $sqlQuery = "SELECT id FROM users WHERE email = '$email'";
    $stmt = $dbConn->prepare($sqlQuery);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    if ($result) {
        // nếu có email trong db thì gửi email
        // send email otp
        // tạo token bằng cách mã hóa email và thời gian
        $token = md5(time() . $email);
        // lưu token vào database
        $query = "insert into PASSWORD_RESETS (email, token)
                        values ('$email', '$token') ";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        // gửi email có link reset mật khẩu
        $link = "<a href='http://127.0.0.1:3000/reset-password?email="
            . $email . "&token=" . $token . "'>Click-Here</a>";
        $mail = new PHPMailer();
        $mail->CharSet = "utf-8";
        $mail->isSMTP();
        $mail->SMTPAuth = true;
        $mail->Username = "lenhatduymb";
        $mail->Password = "yrbfbrzpsobtllgk";
        $mail->SMTPSecure = "ssl";
        $mail->Host = "ssl://smtp.gmail.com";
        $mail->Port = "465";
        $mail->From = "lenhatduymb@gmail.com";
        $mail->FromName = "lê Nhật Duy";
        $mail->addAddress($email, 'Hello');
        $mail->Subject = "Reset Password";
        $mail->isHTML(true);
        $mail->Body = '
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Reset Password</title>
            <style>
                body {
                    background-color: #f2ccb;
                    margin: 0;
                    padding: 0;
                }
        
                .container {
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #f2ccb;
                    padding: 10px;
                    border-radius: 5px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    margin-top: 10px;
                }
        
                h1 {
                    color: red;
                    font-size: 55px;
                }
        
                p {
                    color: black;
                    line-height: 1.6;
                    font-size: 20px;
                }
        
                a {
                    color: red;
                    text-decoration: none;
                    font-weight: bold;
                    font-size: 40px;
                }
        
                a:hover {
                    color: #0056b3;
                }
        
                .signature {
                    color: #999999;
                    margin-top: 5px;
                    font-size: 20px;
                }
            </style>
        </head>
        <body>
            <table width="60%" height="30%" bgcolor="#f2ccb" marginLeft=150>
                <tr>
                    <td align="center">
                        <div class="container">
                            <h1>Yêu cầu đổi mật khẩu</h1>
                            <p>Nhấp vào link bên dưới để đổi mật khẩu</p>
                            <a href="<?php echo $link; ?>"><?php echo $link; ?>' . $link . '</a>
                            <p>Có thắc mắc liên hệ qua email bên dưới.</p>
                            <p class="signature">Make by,<br>lenhatduymb@gmail.com</p>
                        </div>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        
        
';
        $res = $mail->Send();
        if ($res) {
            echo json_encode(array(
                "message" => "Email sent.",
                "status" => true
            ));
        } else {
            echo json_encode(array(
                "message" => "Email sent failed.",
                "status" => false
            ));
        }
    } else {
        // nếu không có email trong db thì trả về thông báo
        echo json_encode(array(
            "status" => false,
            "message" => "Email không tồn tại."
        ));
    }
} catch (\Throwable $th) {
    echo json_encode(array(
        "status" => false,
        "message" => "Có lỗi nè."
    ));
}