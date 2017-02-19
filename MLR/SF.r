#this Explores statistics soncepts
els2002 <- read.csv(file = 'ELS2002-2012.csv',header = TRUE,sep = ',')
income2011 <- els2002$income2011
mean(income2011)
median(income2011)
hist(income2011,breaks = 20)
summary(income2011)
boxplot(income2011)
sd(income2011)

z.test = function(a, mu, sigma){
  z.score = (mean(a) - mu) / (sigma.income / sqrt(length(a)))
  return(z.score)
} #creates a function (“z.test”) that will return the z-score

sample.income = sample(income2011, size=20, replace=FALSE, prob=NULL)#creates a sample of size 20 from income2011, without replacement, and
mu.income = mean(income2011) #specifies that “mu.income” is the mean of income2011
sigma.income = sqrt(sum((income2011-mean(income2011))^2)/length(income2011))
#treats income2011 as a population and specifies that “stdv.income” is equal to σ
z.test(sample.income, mu.income, sigma.income) #calculates the z-statistic for sample.income

ci.lower = function(a, sigma){
  lower.bound = mean(a) - 1.96*(sigma/sqrt(length(a)))
  return(lower.bound)
}

ci.upper = function(a, sigma){
  upper.bound = mean(a) + 1.96*(sigma/sqrt(length(a)))
  return(upper.bound)
}

ci.lower(sample.income, sigma.income)
ci.upper(sample.income, sigma.income)