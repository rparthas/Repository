library('class')
library('gmodels')

#Reads the data and removes id
wbcd <- read.csv("wisc_bc_data.csv", stringsAsFactors = FALSE)
wbcd <- wbcd[-1]
round(prop.table(table(wbcd$diagnosis)) * 100, digits = 1)

# Normalize
normalize <- function(x) {
  return ((x - min(x)) / (max(x) - min(x)))
}

# trains using normalized vector
wbcd_n <- as.data.frame(lapply(wbcd[2:31], normalize))
wbcd_train <- wbcd_n[1:469, ]
wbcd_test <- wbcd_n[470:569, ]
wbcd_train_labels <- wbcd[1:469, 1]
wbcd_test_labels <- wbcd[470:569, 1]
wbcd_test_pred <- knn(train = wbcd_train, test = wbcd_test, cl = wbcd_train_labels, k = 21)
CrossTable(x = wbcd_test_labels, y = wbcd_test_pred,prop.chisq=FALSE)

#trains using z Score
wbcd_z <- as.data.frame(scale(wbcd[-1]))
wbcd_trainz <- wbcd_z[1:469, ]
wbcd_testz <- wbcd_z[470:569, ]
wbcd_test_predz <- knn(train = wbcd_trainz, test = wbcd_testz, cl = wbcd_train_labels, k = 21)
CrossTable(x = wbcd_test_labels, y = wbcd_test_predz,prop.chisq=FALSE)