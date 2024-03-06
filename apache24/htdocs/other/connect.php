<?php
    $con=mysqli_connect("localhost","root","1234","everyhealth");

    $user = $_POST['user_idx'];
    $trainer = $_POST['trainer_idx'];    
    $query1 = "INSERT into connect(user_idx,trainer_idx) values (".$user.",".$trainer.");";
    $insert = mysqli_query($con, $query1);

    mysqli_close($con);
?>