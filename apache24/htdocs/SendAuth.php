<?php
$con=mysqli_connect("localhost","root","1234","jungcar");




$email = $_POST['email'];
$key = $_POST['key'];


header('content-type: text/html; charset=utf-8');



$query = "select id from user where email = '$email'";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

for($i=0;$i<$rowCnt;$i++){
    $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
      $arr[$i]= $row;      
      $to = "$email";             //받는 사람
      $title = '인증번호';            //메일 제목
      $contents = "인증번호는 $key 입니다. 입력해주세요";      //메일 내용
      $headers = 'From: did625@yonsei.ac.kr';  //헤더

mail($to, $title, $contents, $headers);
}



$jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo "$jsonData";

mysqli_close($con);
?>