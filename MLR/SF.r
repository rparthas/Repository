#this Explores statistics soncepts
els2002 <- read.csv(file = 'ELS2002-2012.csv',header = TRUE,sep = ',')
income2011 <- els2002$income2011
mean(income2011)
median(income2011)
hist(income2011,breaks = 20)
summary(income2011)
boxplot(income2011)
sd(income2011)