<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$img = $_POST['img'];
$flag = $_POST['flag'];
$car_idx = $_POST['car_idx'];
//서버에 저장할 파일내용
$filename = "car/".$car_idx."_".$flag.".jpg";
file_put_contents($filename,base64_decode($img));



if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


if($flag==="0"){
    $result=mysqli_query($con, "update car set img = '$filename' where car_idx = $car_idx");
}else{
    $result=mysqli_query($con, "insert into car_img (car_idx,img) values ($car_idx,'$filename')");

}


?>