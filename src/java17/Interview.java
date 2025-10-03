package java17;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
public class Interview {


    // stream creation -   collection.stream(), Arrays(arr).stream(), Stream.of(), Stream.iterate(), Stream.generate()
    // intermediate operations - filter, map, sorted, limit, skip, distinct, flatMap --  these are creating pipepline
    // terminal operations - forEach, collect, reduce, max, min, count, anyMatch, allMatch, noneMatch, findFirst, findAny -- these are used to invoke operation


    public static void main(String[] args) {

        List<String> words = List.of("mark", "kumar","hellondjsk");

        Optional<String> longestWord = words.stream().max(Comparator.comparingInt(String::length));


        System.out.println(longestWord.get());


        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9,10,3,4,5);

        list.stream().sorted(Comparator.reverseOrder()).limit(3).forEach(System.out::println);

        List<Employee> employees = List.of(new Employee(1, "mark", 25, "IT", 1000), new Employee(2, "kumar", 25, "IT", 1000), new Employee(3, "kumar", 25, "IT", 1000));

        // all employees in IT department

       Map<String, List<Employee>> departmentWiseEmployees = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println("departmentwise emp::  " + departmentWiseEmployees);

        // I want each department with 3 highest paid employees

        Map<String, List<Employee>> departmentWiseTop3 = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(Collectors.toList(), e -> e.stream().sorted(Comparator.comparingInt(Employee::getSalary).reversed()).limit(3).collect(Collectors.toList()))));

        System.out.println("departmentWiseTop3  ::  " + departmentWiseTop3);

        list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().filter(entry-> entry.getValue() > 1).forEach(System.out::println);

        // reduce functionality

        int sum = list.stream().reduce(0, Integer::sum);

        System.out.println("sum  ::  " + sum);

        List<List<Integer>> nestedList = List.of(List.of(1,2,3), List.of(4,5,6), List.of(7,8,9));

        List<Integer> flattenedlist = nestedList.stream().flatMap(l-> l.stream()).collect(Collectors.toUnmodifiableList());

        System.out.println("flattenedlist  "+  flattenedlist);
    }

   static class Employee{

        int id;
        String name;
        int age;
        String department;
        int salary;

        public Employee(int id, String name, int age, String department, int salary) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }

        public String getDepartment() {
            return department;
        }

        public int getAge() {
            return age;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getSalary() {
            return salary;
        }

       @Override
       public String toString() {
           return "Employee{" +
                   "age=" + age +
                   ", id=" + id +
                   ", name='" + name + '\'' +
                   ", department='" + department + '\'' +
                   ", salary=" + salary +
                   '}';
       }
   }
}
