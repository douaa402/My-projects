<?php
session_start();
include "dbconnect.php"; 
include "header.phtml";

// Check if the user is authenticated as an admin
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
          window.location.href = 'loginA.php';</script>";
    exit();
}
?>

<br><br>
<div class="container">
    <h2>Modifier livre</h2>
    <hr>
    <?php
    if (isset($_GET["id"])) {
        // Secure the id to prevent SQL injection
        $id = intval($_GET["id"]);

        try {
            // Fetch book details
            $stmt = $pdo->prepare("SELECT * FROM `addbook` WHERE id = :id");
            $stmt->execute(['id' => $id]);

            if ($row5 = $stmt->fetch(PDO::FETCH_ASSOC)) {
                ?>
                <!-- Form for updating book details -->
                <form method="post" enctype="multipart/form-data">
                    <table class='table table-bordered'>
                        <tr>
                            <td><input type="text" name="book_name" value="<?php echo htmlspecialchars($row5['book_name']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td>
                                <?php if (!empty($row5['book_image'])): ?>
                                    <p>Fichier actuel: <?php echo htmlspecialchars($row5['book_image']); ?></p>
                                    <img src="<?php echo htmlspecialchars($row5['book_image']); ?>" alt="Book Image" style="max-width: 100px;">
                                <?php endif; ?>
                                <input type="file" name="f1" class="form-control">
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="book_author_name" value="<?php echo htmlspecialchars($row5['book_author_name']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="book_publication_name" value="<?php echo htmlspecialchars($row5['book_publication_name']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td><input type="date" name="book_purchase_date" value="<?php echo htmlspecialchars($row5['book_purchase_date']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td><input type="number" name="book_price" value="<?php echo htmlspecialchars($row5['book_price']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td><input type="number" name="book_quantity" value="<?php echo htmlspecialchars($row5['book_quantity']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td><input type="number" name="available_quantity" value="<?php echo htmlspecialchars($row5['available_quantity']); ?>" class="form-control" required></td>
                        </tr>
                        <tr>
                            <td><input type="submit" name="submit" value="Modifier livre" class="btn btn-primary"></td>
                        </tr>
                    </table>
                </form>
                <?php
            } else {
                echo "<p>Aucun livre trouvé pour cet identifiant.</p>";
            }
        } catch (PDOException $e) {
            echo "<p>Erreur : " . htmlspecialchars($e->getMessage()) . "</p>";
        }
    }

    if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["submit"])) {
        // Handle form submission
        $book_name = $_POST['book_name'];
        $book_author_name = $_POST['book_author_name'];
        $book_publication_name = $_POST['book_publication_name'];
        $book_purchase_date = $_POST['book_purchase_date'];
        $book_price = $_POST['book_price'];
        $book_quantity = $_POST['book_quantity'];
        $available_quantity = $_POST['available_quantity'];

        // Handle file upload if a new file is selected
        $book_image = $row5['book_image']; // Default to current image
        if (!empty($_FILES['f1']['name'])) {
            $target_dir = "./bookImage/"; // Directory for uploaded images
            
            // Ensure the directory exists
            if (!is_dir($target_dir)) {
                mkdir($target_dir, 0777, true); // Create the directory if it doesn't exist
            }
        
            // Generate a unique file name
            $tm = md5(time());
            $fileName = basename($_FILES["f1"]["name"]);
            $target_file = $target_dir . $tm . "_" . $fileName;
        
            // Move the uploaded file to the target directory
            if (move_uploaded_file($_FILES["f1"]["tmp_name"], $target_file)) {
                $book_image = $target_file; // Save the full path of the uploaded file
            } else {
                echo "<p>Error uploading the image file.</p>";
                $book_image = $row5['book_image']; // Keep the existing image if upload fails
            }
        } else {
            $book_image = $row5['book_image']; // Keep the existing image if no new file is uploaded
        }
        
        

        try {
            // Update the book details in the database
            $query = $pdo->prepare("UPDATE addbook SET 
                book_name = :book_name, 
                book_image = :book_image, 
                book_author_name = :book_author_name, 
                book_publication_name = :book_publication_name, 
                book_purchase_date = :book_purchase_date, 
                book_price = :book_price, 
                book_quantity = :book_quantity, 
                available_quantity = :available_quantity 
                WHERE id = :id");

            $query->execute([
                ':book_name' => $book_name,
                ':book_image' => $book_image,
                ':book_author_name' => $book_author_name,
                ':book_publication_name' => $book_publication_name,
                ':book_purchase_date' => $book_purchase_date,
                ':book_price' => $book_price,
                ':book_quantity' => $book_quantity,
                ':available_quantity' => $available_quantity,
                ':id' => $id
            ]);

            echo "<script>alert('Livre modifié avec succès !');
                  window.location.href = 'display-books.php';</script>";
        } catch (PDOException $e) {
            echo "<p>Erreur : " . htmlspecialchars($e->getMessage()) . "</p>";
        }
    }
    ?>
</div>

<?php include "footer.phtml"; ?>
