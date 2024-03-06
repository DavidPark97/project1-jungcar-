<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");

$user = $_POST['user_idx'];
$routine_idx =$_POST ['routine_idx'];
$getuser = $_POST['get_idx'];


$query1 = "DELETE FROM routine WHERE user_idx = ".$user." and routine_idx = $routine_idx ;";
$result1 = mysqli_query($con, $query1);


$query2 = "Select * from routine where user_idx = $getuser and routine_idx = 1";
$result2 = mysqli_query($con, $query2);

$rowCnt = mysqli_num_rows($result2);


for($i=0;$i<$rowCnt;$i++){
    $row= mysqli_fetch_array($result2);
    $workout_idx = $row['workout_idx'];
    $orders = $row['orders'];
    $targets = $row['targets'];
    $weight = $row['weight'];
    $q = "insert into routine(user_idx,orders,targets,workout_idx,weight,routine_idx) values ($user,$orders,$targets,$workout_idx,$weight,$routine_idx);";
    mysqli_query($con,$q);   
}
mysqli_close($con);
?>