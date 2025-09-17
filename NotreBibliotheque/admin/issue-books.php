<?php
session_start();
include "dbconnect.php"; // Assurez-vous que dbconnect.php utilise PDO pour la connexion.
include "header.phtml";

// Check if admin is logged in
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
}

?>

<br><br>
<div class="container">
    <h2>Les livres publiés</h2>
    <hr>
    <form name="form1" action="" method="post">
    <table>
            <tr>
                <td>
                    <select name="enr" class="form-control selectpicker" style="width:400px">
                        <?php
                        try {
                            // Récupérer les identifiants d'inscription
                            $stmt = $pdo->query("SELECT enrollment FROM `inscription-adhérent` ");
                            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                                echo "<option>" . htmlspecialchars($row['enrollment']) . "</option>";
                            }
                        } catch (PDOException $e) {
                            echo "<option>Erreur : " . htmlspecialchars($e->getMessage()) . "</option>";
                        }
                        ?>
                    </select>
                </td>
                <td>
                    <input type="submit" name="submit" value="Chercher" class="btn btn-primary">
                </td>
            </tr>
        </table>

        <?php
        // If user has submitted enrollment search
        if (isset($_POST['submit'])) {
            $enrollment = $_POST['enr'] ?? '';
            try {
                // Fetch user details
                $stmt = $pdo->prepare("SELECT * FROM `inscription-adhérent` WHERE enrollment = :enrollment");
                $stmt->execute(['enrollment' => $enrollment]);

                if ($row5 = $stmt->fetch(PDO::FETCH_ASSOC)) {
                    $_SESSION["enrollment"] = $row5["enrollment"];
                    $_SESSION["prenom"] = $row5["prenom"];
                    ?>
                    <table class='table table-bordered'>
                        <tr>
                            <td><input type="text" name="enrollment" value="<?php echo htmlspecialchars($row5['enrollment']); ?>" class="form-control" disabled></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="studentname" value="<?php echo htmlspecialchars($row5['nom'] . ' ' . $row5['prenom']); ?>" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="studentsem" value="<?php echo htmlspecialchars($row5['sem']); ?>" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="studentcontact" value="<?php echo htmlspecialchars($row5['telephone']); ?>" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="studentmail" value="<?php echo htmlspecialchars($row5['mail']); ?>" class="form-control"></td>
                        </tr>
                        <tr>
                            <td>
                                <select name="booksname" class="form-control selectpicker">
                                    <?php
                                    // Fetch book names
                                    $booksStmt = $pdo->query("SELECT book_name FROM `addbook`");
                                    while ($book = $booksStmt->fetch(PDO::FETCH_ASSOC)) {
                                        echo "<option>" . htmlspecialchars($book['book_name']) . "</option>";
                                    }
                                    ?>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="booksissuedate" value="<?php echo date('Y-m-d'); ?>" class="form-control"></td>
                        </tr>
                        <tr>
                            <td><input type="submit" name="submit2" value="Publier" class="btn btn-primary"></td>
                        </tr>
                    </table>
                    <?php
                } else {
                    echo "<p>Aucun enregistrement trouvé pour cet identifiant.</p>";
                }
            } catch (PDOException $e) {
                echo "<p>Erreur : " . htmlspecialchars($e->getMessage()) . "</p>";
            }
        }

        // If user is issuing a book
        if (isset($_POST['submit2'])) {
            try {
                // Vérifier la disponibilité du livre
                $stmt = $pdo->prepare("SELECT available_quantity FROM addbook WHERE book_name = :booksname");
                $stmt->execute([':booksname' => $_POST['booksname']]);
                $book = $stmt->fetch();

                if ($book && $book['available_quantity'] > 0) {
                    // Insérer les détails du livre publié
                    $stmt = $pdo->prepare("INSERT INTO `issue_books` 
                    (enrollment, student_name, student_sem, student_contact, student_email, books_name, books_issue_date, prenom) 
                    VALUES (:enrollment, :student_name, :student_sem, :student_contact, :student_email, :books_name, :books_issue_date, :prenom)");

                    $stmt->execute([
                        ':enrollment' => $_SESSION['enrollment'],
                        ':student_name' => $_POST['studentname'],
                        ':student_sem' => $_POST['studentsem'],
                        ':student_contact' => $_POST['studentcontact'],
                        ':student_email' => $_POST['studentmail'],
                        ':books_name' => $_POST['booksname'],
                        ':books_issue_date' => $_POST['booksissuedate'],
                        ':prenom' => $_SESSION['prenom']
                    ]);

                    // Réduire la quantité disponible
                    $stmt = $pdo->prepare("UPDATE addbook SET available_quantity = available_quantity - 1 WHERE book_name = :booksname");
                    $stmt->execute([':booksname' => $_POST['booksname']]);

                    echo "<script>alert('Livres publiés avec succès.'); window.location.href = window.location.href;</script>";
                } else {
                    echo "<script>alert('Le livre n\'est pas disponible ou en stock insuffisant.');</script>";
                }
            } catch (PDOException $e) {
                echo "<p>Erreur lors de l'émission : " . htmlspecialchars($e->getMessage()) . "</p>";
            }
        }
        ?>
    </form>
</div>

<?php include "footer.phtml"; ?>
