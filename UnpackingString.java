import java.util.Stack;
import java.util.Scanner;

public class UnpackingString {

    public String str;

    public UnpackingString(String str) {//конструктор класса
        this.str = str;
    }

    private int getBeginIndex(int endIndex) {//возвращает начальный индекс разворачиваемой скобки
        while (str.charAt(endIndex) != '[') {//пока не будет встречена открывающая скобка
            endIndex--;
        }
        endIndex--;//переход на число перед скобкой
        while (endIndex >= 0 && Character.isDigit(str.charAt(endIndex))) {//пока не закончится число повторений 
            endIndex--;
        }
        return endIndex + 1;//возврат индекса,с которого начинается число повторений
    }

    void unpackStr() {//осуществляет "распаковку" строки
        int index = 0;//индекс текущего рассматриваемеого символа
        while (index < str.length()) {//пока не будет пройдена вся строка 
            if (str.charAt(index) == ']') {
                int begin = getBeginIndex(index);//индекс начала числа повторений
                String repeatStr = str.substring(str.indexOf('[', begin) + 1, str.indexOf(']', begin));//строка которую нужно повторить
                int repeatNumber = Integer.parseInt(str.substring(begin, str.indexOf('[', begin)));//количество повторений
                str = str.replaceFirst(repeatNumber + "\\[" + repeatStr + "\\]", repeatStr.repeat(repeatNumber));//замена скобок на строку из повторов
                index = begin;//перевод индекса текущего элемента для продолжения чтения
            } else {
                index++;
            }
        }
    }

    private boolean isLetter(char ch) {/*возвращает true, если символ является латинской буквой
        так как по условиям разрещены только символы латинского алфовита
         */
        return (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122);
    }

    boolean isValid() {//возвращает true, если строка валидна
        Stack<Character> braces = new Stack();//Стек для хранения скобок
        boolean numberFound = false;//флаг показывающий найдено ли число
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!numberFound && isLetter(ch)) {//если не встречено число и символ - латинская буква
                continue;//переход к следующей итерации цикла
            } else if (Character.isDigit(ch)) {//если символ цифра
                numberFound = true;//переключение флага
            } else if (numberFound && ch == '[') {//если была встречена цифра и символ скобка
                numberFound = false;//переключение флага
                braces.add(ch);//добваление скобки в стек
            } else if (!numberFound && ch == ']') {//если не было встречено числа и скобка закрывающая
                if (braces.size() > 0) {//если стек пустой, значит скобка лишняя
                    braces.pop();//извлеченеи открывающей скобки
                }
            } else {//еслин не был выполнен ни один if символ является недопустимым
                return false;
            }
        }
        return braces.isEmpty();//если в стеке осталось открывающая скобка, значит она не имеет парную закрывающую
    }

    public static void main(String[] args) {
        Scanner scaner = new Scanner(System.in);
        UnpackingString sp = new UnpackingString(scaner.nextLine());
        if (sp.isValid()){
            sp.unpackStr();
            System.out.println(sp.str);
        } else {
            System.out.println("Invalid input");
        }
    }
}
