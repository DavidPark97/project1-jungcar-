<?php
    $con=mysqli_connect("localhost","root","1234","everyhealth");

    $user = $_POST['user'];
    $inbody = $_POST['inbody'];
    $preference = $_POST['preference'];
    $category = $_POST['part'];
    $gender = $_POST['gender'];
    
	$wei = $_POST['wei'];
	$hei = $_POST['hei'];
	
    $query1 = "INSERT into user(user_idx,inbody,preference,category,gender) values (".$user.",".$inbody.",".$preference.",".$category.",".$gender.");";
    $insert = mysqli_query($con, $query1);
    $query2 = "update user_login set weight=$wei, height=$hei  where user_idx=$user";
    $insert2 = mysqli_query($con,$query2);

    mysqli_close($con);
?>