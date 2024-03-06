<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$type = $_POST['type'];
$idx = $_POST['idx'];



header('content-type: text/html; charset=utf-8');

if(strcmp($type,"recomment")==0){
    $query = "delete from recomment where recomment_idx=$idx";
    $result=mysqli_query($con, $query);

}else if(strcmp($type,"comment")==0){
    $query = "delete from recomment where comment_idx=$idx";
    $result=mysqli_query($con, $query);
    $query3 = "delete from cmt_reco where comment_idx=$idx";
    $result3=mysqli_query($con, $query3);
    $query2 = "delete from comment where comment_idx =$idx";
    $result2=mysqli_query($con, $query2);
}



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();




mysqli_close($con);
?>