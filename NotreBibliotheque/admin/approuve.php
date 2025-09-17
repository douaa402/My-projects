<?php
include "dbconnect.php";

$id = intval($_GET["id"]); // Ensure $id is an integer to prevent SQL injection

try {
    // Correct SQL query
    $query = $pdo->prepare("UPDATE `inscription-adhérent` SET statut = 'oui' WHERE id = :id");

    // Bind the ID parameter
    $query->bindParam(':id', $id, PDO::PARAM_INT);

    // Execute the query
    $query->execute();
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
    exit; 
}
?>
<script type="text/javascript">
    window.location = "display-student-info.php";
</script>
