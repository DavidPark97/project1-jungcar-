<?php
$con=mysqli_connect("localhost","root","1234","jungcar");


$id = $_POST['id'];
$passwd=$_POST['passwd'];


if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$query = "select user_idx from user where id = '$id' and passwd='$passwd'";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$rowCnt = 0;
$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

if($rowCnt!=0){

    $arr= array();
    for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
    }
}else{
    $result2=mysqli_query($con, "select null as user_idx");
    $user = mysqli_fetch_array($result2);
    $user_idx = $user['user_idx'];
    $arr= array("user_idx"=>$user_idx);
}


  
$jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;
mysqli_close($con);
?>