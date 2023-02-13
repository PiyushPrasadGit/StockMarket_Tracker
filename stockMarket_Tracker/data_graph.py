import pandas as pd
from matplotlib import pyplot as plt
import os
import plotly.graph_objects as go
from plotly.subplots import make_subplots

cols=['Date', 'Time', 'Day', 'Opening Price', 'Closing Price', 'Max Price', 'Min Price', 'Trend']
df_1=pd.read_csv(os.getcwd()+"\\src\\data.csv", index_col=False)
df_1.columns=cols

fig=make_subplots(specs=[[{"secondary_y":True}]])
fig.add_trace(go.Candlestick(x=(df_1['Date']+df_1['Time']),
                             open=df_1['Opening Price'],
                             high=df_1['Max Price'],
                             low=df_1['Min Price'],
                             close=df_1['Closing Price']))
fig.show()
