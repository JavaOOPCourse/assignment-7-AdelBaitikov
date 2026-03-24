import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordProcessor  {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______
    class Student{
        String name;
        int gpa;
        Student(String name, int gpa){
            this.name = name;
            this.gpa = gpa;
        }
    }

    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("input/students.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length < 2) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                try {
                    String name = parts[0].trim();
                    int gpa = Integer.parseInt(parts[1].trim());

                    if (gpa < 0 || gpa > 100) {
                        throw new InvalidScoreException("Invalid score: " + gpa);
                    }

                    students.add(new Student(name, gpa));

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData(){
        // TODO: обработка данных и сортировка здесь
        int sum = 0;

        for (Student s : students) {
            sum += s.gpa;

            if (highestStudent == null || s.gpa > highestStudent.gpa) {
                highestStudent = s;
            }
        }

        if (!students.isEmpty()) {
            averageScore = (double) sum / students.size();
        }

        // сортировка по убыванию
        students.sort((a, b) -> b.gpa - a.gpa);
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))){

            bw.write("Average: " + averageScore);
            bw.newLine();

            if (highestStudent != null) {
                bw.write("Highest: " + highestStudent.name + " - " + highestStudent.gpa);
                bw.newLine();
            }

            bw.write("Sorted:");
            bw.newLine();

            for (Student s : students) {
                bw.write(s.name + " - " + s.gpa);
                bw.newLine();
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Готово");
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

// class Student (name, score)