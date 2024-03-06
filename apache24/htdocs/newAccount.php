<?php
$con=mysqli_connect("localhost","root","1234","jungcar");


$id = $_POST['id'];
$passwd=$_POST['passwd'];
$phone = $_POST['phone'];
$email=$_POST['email'];

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$query = "select * from user where id = '$id'";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$rowCnt = 0;
$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

if($rowCnt!=0){
 
    $result2=mysqli_query($con, "select null as user_idx");
    $user = mysqli_fetch_array($result2);
    $user_idx = $user['user_idx'];
}else{

    $result2=mysqli_query($con, "select max(user_idx)+1 as user_idx from user");
    $user = mysqli_fetch_array($result2);
    $user_idx = $user['user_idx'];
    if($user_idx==NULL){
    $user_idx="1";
    }

    $query2 = "insert into user (user_idx,id,passwd,phone,email) values ($user_idx,'$id','$passwd','$phone','$email')";
    mysqli_query($con,$query2);
    
}

$arr=array("user_idx" => $user_idx);
$arr2=array($arr);
  
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;
mysqli_close($con);
?>