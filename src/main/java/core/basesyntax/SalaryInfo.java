package core.basesyntax;

public class SalaryInfo {
    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        String[] fromParts = dateFrom.split("\\.");
        String[] toParts = dateTo.split("\\.");

        int fromD = Integer.parseInt(fromParts[0]);
        int fromM = Integer.parseInt(fromParts[1]);
        int fromY = Integer.parseInt(fromParts[2]);

        int toD = Integer.parseInt(toParts[0]);
        int toM = Integer.parseInt(toParts[1]);
        int toY = Integer.parseInt(toParts[2]);

        int[] sums = new int[names.length];

        for (String record : data) {
            String[] parts = record.split("\\s+");
            if (parts.length < 4) {
                continue;
            }

            String[] dateParts = parts[0].split("\\.");
            int d = Integer.parseInt(dateParts[0]);
            int m = Integer.parseInt(dateParts[1]);
            int y = Integer.parseInt(dateParts[2]);

            if (!isInRange(d, m, y, fromD, fromM, fromY, toD, toM, toY)) {
                continue;
            }

            String worker = parts[1];
            int hours = Integer.parseInt(parts[2]);
            int rate = Integer.parseInt(parts[3]);
            int earned = hours * rate;

            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(worker)) {
                    sums[i] += earned;
                    break;
                }
            }
        }

        String ls = System.lineSeparator();
        StringBuilder builder = new StringBuilder();
        builder.append("Report for period ").append(dateFrom).append(" - ")
                .append(dateTo).append(ls);

        for (int i = 0; i < names.length; i++) {
            builder.append(names[i]).append(" - ").append(sums[i]);
            if (i != names.length - 1) {
                builder.append(ls);
            }
        }

        return builder.toString();
    }

    private boolean isInRange(int d, int m, int y,
                              int fromD, int fromM, int fromY,
                              int toD, int toM, int toY) {
        if (y < fromY || y > toY) {
            return false;
        }
        if (y == fromY) {
            if (m < fromM) {
                return false;
            }
            if (m == fromM && d < fromD) {
                return false;
            }
        }
        if (y == toY) {
            if (m > toM) {
                return false;
            }
            if (m == toM && d > toD) {
                return false;
            }
        }
        return true;
    }
}
