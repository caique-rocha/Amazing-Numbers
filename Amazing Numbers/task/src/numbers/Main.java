package numbers;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        printWelcome();


        String request;
        long number;
        long repetitions;
        while (true) {
            System.out.print("\nEnter a request: ");
            request = scanner.nextLine();
            String[] parameters = request.split(" ");
            String[] filter = {};

            if (parameters.length == 0) {
                printWelcome();
            } else if (parameters.length == 1) {

                try {
                    number = Long.parseLong(parameters[0]);

                    if (number == 0) {
                        break;
                    } else if (number < 1) {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");

                    } else {

                        printInfoNumber(number);

                    }
                } catch (Exception e) {
                    System.out.println("\nThe first parameter should be a natural number or zero.");
                }
            } else if (parameters.length == 2) {

                try {
                    number = Long.parseLong(parameters[0]);
                    if (number < 1) {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    }
                } catch (Exception e) {
                    System.out.println("\nThe first parameter should be a natural number or zero.");
                    continue;
                }

                try {
                    repetitions = Long.parseLong(parameters[1]);
                    if (repetitions < 1) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                    }
                } catch (Exception e) {
                    System.out.println("\nThe first parameter should be a natural number or zero.");
                    continue;
                }

                printListInfoNumbers(number, repetitions, filter);

            } else  {

                String[] requestSplit = request.split(" ");

                filter = Arrays.copyOfRange(requestSplit, 2, requestSplit.length);

                try {
                    number = Long.parseLong(parameters[0]);
                    if (number < 1) {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    }
                } catch (Exception e) {
                    System.out.println("\nThe first parameter should be a natural number or zero.");
                    continue;
                }

                try {
                    repetitions = Long.parseLong(parameters[1]);
                    if (repetitions < 1) {
                        System.out.println("\nThe second parameter should be a natural number.\n");
                    }
                } catch (Exception e) {
                    System.out.println("\nThe first parameter should be a natural number or zero.");
                    continue;
                }

                if (!isOption(filter)) {

                    if (parameters.length == 3) {
                        System.out.printf("\nThe property [%s] is wrong.\n", parameters[2]);
                    } else {

                        StringBuilder wrongFilters = new StringBuilder();
                        int countWrong = 0;
                        for (String s : filter) {
                            if (!isOption(new String[]{s})) {
                                countWrong++;
                                wrongFilters.append(s).append(", ");
                            }
                        }

                        if (countWrong == 1) {
                            System.out.printf("\nThe property [%s] is wrong.\n",
                                    wrongFilters.substring(0, wrongFilters.length() - 2));
                        } else {
                            System.out.printf("\nThe properties [%s] are wrong.\n",
                                    wrongFilters.substring(0, wrongFilters.length() - 2));
                        }

                    }

                    printAvailableProperties();
                } else if (isMutuallyExclusive(filter)) {

                    System.out.println("\nThe request contains mutually exclusive properties.");
                    System.out.println("There are no numbers with these properties.");

                } else {

                    printListInfoNumbers(number, repetitions, filter);
                }
            }

        }
        System.out.println("\nGoodbye!");
    }


    private static boolean endsWith7(long number) {
        return number % 10 == 7;
    }

    private static boolean divisibleBy7(long number) {
        return number % 7 == 0;
    }

    private static boolean isEven(long number) {
        return number % 2 == 0;
    }

    private static boolean isBuzz(long number) {
        return endsWith7(number) || divisibleBy7(number);
    }

    private static boolean isDuck(long number) {

        return String.valueOf(number).contains("0") && !String.valueOf(number).startsWith("0");
    }

    private static boolean isPalindromic(long number) {

        boolean palindromic = true;
        String numberString = String.valueOf(number);
        for (int i = 0; i < numberString.length() / 2; i++) {

            if (numberString.charAt(i) != numberString.charAt(numberString.length() - 1 - i)) {
                palindromic = false;
                break;
            }
        }

        return palindromic;
    }

    private static boolean isSpy(long number) {

        char[] numberArray = String.valueOf(number).toCharArray();
        long sum = 0;
        long multiplication = 1;

        for (char c : numberArray) {

            long digit = Long.parseLong(String.valueOf(c));
            sum += digit;
            multiplication *= digit;
        }

        return sum == multiplication;
    }

    private static boolean isGapful(long number) {

        char[] numberArray = String.valueOf(number).toCharArray();
        if (numberArray.length < 3) {
            return false;
        }
        int divider = Integer.parseInt(numberArray[0] + String.valueOf(numberArray[numberArray.length - 1]));

        return number % divider == 0;
    }

    private static boolean isSquare(long number) {

        return number == Math.pow((int) Math.sqrt(number), 2);
    }

    private static boolean isSunny(long number) {

        return isSquare(number + 1);
    }

    private static boolean isJumping(long number) {

         String numberString = String.valueOf(number);

        for (int i = 1; i < numberString.length(); i++) {
            if ((int) numberString.charAt(i - 1) - (int) numberString.charAt(i) > 1 ||
                    (int) numberString.charAt(i - 1) - (int) numberString.charAt(i) < -1 ||
                    (int) numberString.charAt(i - 1) - (int) numberString.charAt(i) == 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean isHappy(long number) {

        if (number == 1) {
            return true;
        } else if (number == 4) {
            return false;
        } else {
            return isHappy(sumOfDigits(number));
        }
    }

    private static long sumOfDigits(long number) {
        int sum = 0;
        while(number > 0) {
            sum += Math.pow(number % 10, 2);
            number = number / 10;
        }
        return sum;
    }

    /**
     * Verifica se os filtros fornecidos são opções válidas.
     *
     * @param filters os filtros a serem verificados
     * @return true se todos os filtros são opções válidas, caso contrário, false
     */
    private static boolean isOption(String[] filters) {

        int validOptionCount = 0;

        // Lista de opções válidas
        String[] options = {
                "even", "odd", "buzz", "duck", "palindromic" , "gapful", "spy", "square", "sunny", "jumping",
                "happy", "sad",
                "-even", "-odd", "-buzz", "-duck", "-palindromic" , "-gapful", "-spy", "-square", "-sunny", "-jumping",
                "-happy", "-sad"
        };

        // Verificar cada filtro fornecido
        for (String filter : filters) {
            for (String str : options) {
                if (filter.toLowerCase().equals(str)) {
                    validOptionCount++;
                }
            }
        }

        // Verificar se o número de opções válidas é igual ao número de filtros fornecidos
        return validOptionCount == filters.length;
    }

    /**
     * Verifica se os filtros fornecidos são mutuamente exclusivos.
     * Dois filtros são considerados mutuamente exclusivos se não podem ser aplicados simultaneamente.
     *
     * @param filters Os filtros a serem verificados.
     * @return true se os filtros forem mutuamente exclusivos, false caso contrário.
     */
    private static boolean isMutuallyExclusive(String[] filters) {

        for (int i = 0; i < filters.length; i++) {

            for (int j = i; j < filters.length; j++) {

                if (filters[i].equals("even") && filters[j].equals("odd") ||
                        filters[i].equals("duck") && filters[j].equals("spy") ||
                        filters[i].equals("sunny") && filters[j].equals("square") ||

                        (filters[j].equals("even") && filters[i].equals("odd") ||
                        filters[j].equals("duck") && filters[i].equals("spy") ||
                        filters[j].equals("sunny") && filters[i].equals("square")) ||

                        filters[i].equals("-even") && filters[j].equals("-odd") ||
                        filters[i].equals("-duck") && filters[j].equals("-spy") ||
                        filters[i].equals("-sunny") && filters[j].equals("-square") ||

                        (filters[j].equals("-even") && filters[i].equals("-odd") ||
                                filters[j].equals("-duck") && filters[i].equals("-spy") ||
                                filters[j].equals("-sunny") && filters[i].equals("-square")) ||
                        // verifica se dois filtros não estão se anulando ex: odd e -odd
                        filters[i].equals("-" + filters[j]) || filters[j].equals("-" + filters[i])) {
                    return true;
                }
            }
        }

        return false;

    }

    private static void printInfoNumber(long number) {

        System.out.printf("\nProperties of %d\n", number);
        System.out.printf("\t\tbuzz: %s\n", isBuzz(number));
        System.out.printf("\t\tduck: %s\n", isDuck(number));
        System.out.printf(" palindromic: %s\n", isPalindromic(number));
        System.out.printf("\t  gapful: %s\n", isGapful(number));
        System.out.printf("\t     spy: %s\n", isSpy(number));
        System.out.printf("\t  square: %s\n", isSquare(number));
        System.out.printf("\t   sunny: %s\n", isSunny(number));
        System.out.printf("\t jumping: %s\n", isJumping(number));
        System.out.printf("\t\teven: %s\n", isEven(number));
        System.out.printf("\t\t odd: %s\n", !isEven(number));
        System.out.printf("\t   happy: %s\n", isHappy(number));
        System.out.printf("\t\t sad: %s\n", !isHappy(number));


    }

    private static void printAvailableProperties() {

        System.out.println(
                "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, " +
                        "HAPPY, SAD]"
        );
    }

    /**
     * Verifica se um número atende a um filtro especificado.
     *
     * @param filter O filtro a ser aplicado.
     * @param number O número a ser verificado.
     * @return true se o número atender ao filtro, false caso contrário.
     */
    private static boolean matchFilter(String filter, long number) {
        if (filter.equals("even") && !isEven(number)) {
            return false;
        } else if (filter.equals("odd") && isEven(number)) {
            return false;
        } else if (filter.equals("buzz") && !isBuzz(number)) {
            return false;
        } else if (filter.equals("duck") && !isDuck(number)) {
            return false;
        } else if (filter.equals("palindromic") && !isPalindromic(number)) {
            return false;
        } else if (filter.equals("gapful") && !isGapful(number)) {
            return false;
        } else if (filter.equals("spy") && !isSpy(number)) {
            return false;
        } else if (filter.equals("square") && !isSquare(number)) {
            return false;
        } else if (filter.equals("sunny") && !isSunny(number)) {
            return false;
        } else if (filter.equals("jumping") && !isJumping(number)) {
            return false;
        } else if (filter.equals("happy") && !isHappy(number)) {
            return false;
        } else if (filter.equals("sad") && isHappy(number)) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * Exibe as propriedades em uma lista de números consecutivos.
     *
     * @param number      O número inicial da lista.
     * @param repetitions O número de repetições na lista.
     * @param filters     Os filtros a serem aplicados à lista de números.
     */
    private static void printListInfoNumbers(long number, long repetitions, String[] filters) {

        long n = number;
        int i = 0;
        System.out.println();

        while (i < repetitions) {
            if (filters.length == 0) {
                // Se nenhum filtro for especificado
                printFormattedProprieties(n);
                n++;
                i++;
            } else {
                // Se filtros forem especificados

                int isFilter = 0;
                for (String filter : filters) {
                    // Verifica cada filtro

                    if (!matchFilter(filter.toLowerCase(), n) || (filter.startsWith("-") &&
                            matchFilter(filter.substring(1).toLowerCase(), n))) {
                        // Verifica se o número atende ao filtro ou se o filtro negado não é atendido
                        n++;
                        break;
                    } else {
                        isFilter++;
                    }
                }
                if (isFilter == filters.length) {
                    // Se o número não atender a nenhum filtro

                    printFormattedProprieties(n);
                    n++;
                    i++;
                }
            }
        }
    }

    /**
     * Imprime as propriedades formatadas de um número.
     *
     * @param number O número para o qual imprimir as propriedades formatadas.
     */
    private static void printFormattedProprieties(long number) {

        System.out.printf("\t\t\t%d is %s%s%s%s%s%s%s%s%s%s\n", number, isBuzz(number) ? "buzz, " : "",
                isDuck(number) ? "duck, " : "", isPalindromic(number) ? "palindromic, " : "",
                isSpy(number) ? "spy, " : "", isGapful(number) ? "gapful, " : "", isSquare(number) ? "square, " : "",
                isSunny(number) ? "sunny, " : "", isJumping(number) ? "jumping, " : "",
                isEven(number) ? "even, " : "odd, ", isHappy(number) ? "happy" : "sad");
    }

    /**
     * Exibe uma mensagem de boas-vindas e fornece informações sobre os pedidos suportados.
     */
    private static  void printWelcome() {

        System.out.println("""
                Welcome to Amazing Numbers!
                                
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                * the first parameter represents a starting number;
                * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
    }
}
