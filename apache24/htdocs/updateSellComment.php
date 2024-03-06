<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$type = $_POST['type'];
$idx = $_POST['idx'];
$comment = $_POST['comment'];


header('content-type: text/html; charset=utf-8');

if(strcmp($type,"recomment")==0){
    $query = "update sell_recomment set content='$comment' where sell_recomment_idx=$idx";
    $result=mysqli_query($con, $query);

}else if(strcmp($type,"comment")==0){
    $query = "update sell_comment set content='$comment' where sell_comment_idx=$idx";
    $result=mysqli_query($con, $query);

}



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();




mysqli_close($con);
?>