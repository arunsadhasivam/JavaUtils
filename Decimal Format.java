

public static double getDecFmt(double value, int noOfDigit) {
            Double result = null;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(noOfDigit);
            df.setMinimumFractionDigits(1);
            df.setGroupingUsed(false);
            // rounded to 2 significant digits
            String valIndex = String.valueOf(value);
            int index = valIndex.indexOf(CommonConstants.DOT)+1;
            String decInitVal = valIndex.substring(index, valIndex.length());
            if (noOfDigit >= 4 && !decInitVal.equals("0")) {
                  value = value + .005;
                  String val[] = String.valueOf(value).split(CommonConstants.DECIMAL_DELIMITER);
                  String intPart = val[0];
                  String floatPart = val[1];
                  double d = Double.parseDouble(floatPart);
                  String fmtDouble = df.format(d);
                  fmtDouble = fmtDouble.substring(0, 2);
                  String strResult = intPart + CommonConstants.DOT + fmtDouble ;
                  result = Double.parseDouble(strResult);

                  return result;
            }
            result = new Double(df.format(value));
            return result;
      }
