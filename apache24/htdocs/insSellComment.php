<?php
$con=mysqli_connect("localhost","root","1234","jungcar");




$user_idx = $_POST['user_idx'];
$sell_idx = $_POST['sell_idx'];
$content = $_POST['content'];



$now = date("Y-m-d H:i:s");


header('content-type: text/html; charset=utf-8');




	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

    $query = "insert into sell_comment(user_idx,sell_idx,content,date) values ($user_idx,$sell_idx,'$content','$now')";


$result=mysqli_query($con, $query);


mysqli_close($con);

?>