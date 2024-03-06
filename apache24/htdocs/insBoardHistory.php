<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user_idx = $_POST['user_idx'];
$board_idx = $_POST['board_idx'];


$now = date("Y-m-d");


header('content-type: text/html; charset=utf-8');



$query = "select * from shboard where user_idx = $user_idx and board_idx =$board_idx";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$rowCnt = 0;
$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

if($rowCnt!=0){
 
}else{
    $query2 = "insert into shboard (user_idx,board_idx,date) values ($user_idx,$board_idx,'$now')";
    $result2=mysqli_query($con, $query2);

}


mysqli_close($con);
?>