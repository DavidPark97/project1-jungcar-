<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$type = $_POST['type'];
$idx = $_POST['idx'];



header('content-type: text/html; charset=utf-8');

if(strcmp($type,"recomment")==0){
    $query = "delete from sell_recomment where sell_recomment_idx=$idx";
    $result=mysqli_query($con, $query);

}else if(strcmp($type,"comment")==0){
    $query = "delete from sell_recomment where sell_comment_idx=$idx";
    $result=mysqli_query($con, $query);
    $query2 = "delete from sell_comment where sell_comment_idx =$idx";
    $result2=mysqli_query($con, $query2);
}



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();




mysqli_close($con);
?>