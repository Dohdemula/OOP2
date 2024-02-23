import java.util.*;

class Student {
    private int id;
    private String name;
    private int grade;

    public Student(int id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }
}

class FeeStructure {
    private String feeType;
    private double amount;
    private Date dueDate;

    public FeeStructure(String feeType, double amount, Date dueDate) {
        this.feeType = feeType;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public String getFeeType() {
        return feeType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDueDate() {
        return dueDate;
    }
}

class Payment {
    private int paymentId;
    private int studentId;
    private double amount;
    private Date paymentDate;
    private String paymentMethod;

    public Payment(int paymentId, int studentId, double amount, Date paymentDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}

public class Main {
    private List<Student> students = new ArrayList<>();
    private Map<Integer, FeeStructure> feeStructureMap = new HashMap<>();
    private Map<Integer, List<Payment>> paymentRecordsMap = new HashMap<>();
    private Set<Integer> overduePaymentsSet = new HashSet<>();

    public void addStudent(int id, String name, int grade) {
        students.add(new Student(id, name, grade));
    }

    public void addFeeStructure(int grade, String feeType, double amount, Date dueDate) {
        feeStructureMap.put(grade, new FeeStructure(feeType, amount, dueDate));
    }

    public void recordPayment(int studentId, double amount, String paymentMethod) {
        Date currentDate = new Date();
        Payment payment = new Payment(paymentRecordsMap.size() + 1, studentId, amount, currentDate, paymentMethod);
        List<Payment> payments = paymentRecordsMap.getOrDefault(studentId, new LinkedList<>());
        payments.add(payment);
        paymentRecordsMap.put(studentId, payments);
    }

    public void notifyOverduePayments() {
        Date currentDate = new Date();
        for (Map.Entry<Integer, FeeStructure> entry : feeStructureMap.entrySet()) {
            int grade = entry.getKey();
            FeeStructure feeStructure = entry.getValue();
            Date dueDate = feeStructure.getDueDate();
            if (currentDate.after(dueDate)) {
                overduePaymentsSet.add(grade);
            }
        }

        for (Map.Entry<Integer, List<Payment>> entry : paymentRecordsMap.entrySet()) {
            int studentId = entry.getKey();
            List<Payment> payments = entry.getValue();
            for (Payment payment : payments) {
                if (overduePaymentsSet.contains(studentId)) {
                    System.out.println("Student ID: " + studentId + " has an overdue payment of " + payment.getAmount() + " for " + payment.getPaymentMethod());
                }
            }
        }
    }

    public static void main(String[] args) {
        Main program = new Main();

        program.addStudent(1, "John Doe", 10);
        program.addStudent(2, "Jane Smith", 11);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 29);
        program.addFeeStructure(10, "Tuition Fee", 1000.0, calendar.getTime());

        program.recordPayment(1, 800.0, "Online Payment");

        program.notifyOverduePayments();
    }
}
