<?php
$con=mysqli_connect("localhost","root","1234","jungcar");




$user_idx = $_POST['user_idx'];
$comment_idx = $_POST['comment_idx'];
$content = $_POST['content'];



$now = date("Y-m-d H:i:s");


header('content-type: text/html; charset=utf-8');




	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

    $query = "insert into sell_recomment(user_idx,sell_comment_idx,content,date) values ($user_idx,$comment_idx,'$content','$now')";


$result=mysqli_query($con, $query);


mysqli_close($con);

?>