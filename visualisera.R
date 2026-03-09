library(plotly)

setwd("C:/Users/joakim/IdeaProjects/inlamning2_funk")
library(plotly)

df <- read.csv("top3.csv")

df_other <- subset(df, group == "other")
df_top3 <- subset(df, group != "other")

fig <- plot_ly()

fig <- add_trace(
  fig,
  data = df_other,
  x = ~x,
  y = ~y,
  z = ~z,
  type = "scatter3d",
  mode = "markers",
  marker = list(size = 1, color = "lightgray"),
  opacity = 0.15,
  showlegend = FALSE
)

fig <- add_trace(
  fig,
  data = df_top3,
  x = ~x,
  y = ~y,
  z = ~z,
  type = "scatter3d",
  mode = "markers",
  color = ~group,
  marker = list(size = 3)
)

fig