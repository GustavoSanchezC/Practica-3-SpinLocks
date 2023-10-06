import pandas as pd
import matplotlib.pyplot as plt

#  tiempos en segundos para diferentes tipos de bloqueo 
#Fernando
taslock_times = [6.324, 9.242, 11.032, 10.502, 9.015, 18.574, 12.798]
ttaslock_times = [4.33, 6.453, 8.589, 7.636, 12.239, 10.956, 6.806 ]
backofflock_times = [ 2.994, 3.808, 5.329, 7.636, 7.941, 9.665, 9.548]
clhlock_times = [9.141, 11.856, 14.423,  65.677, 80.883, 93.745, 166.19]
mcslock_times = [9.279, 10.355, 15.869, 62.72, 110.825,97.667,168.446]


#Gustavo
"""taslock_times = [2.899, 3.385, 6.114, 11.49, 15.692, 16.37, 16.876]
ttaslock_times = [2.493, 4.861, 7.772, 9.590, 14.297, 10.838, 11.471]
backofflock_times = [3.247, 3.885, 4.412, 4.862, 4.831, 5.267, 6.21]
clhlock_times = [6.280, 8.546, 12.113, 18.344, 66, 81.6, 137.4]
mcslock_times = [6.580, 6.691, 11.239, 16.524, 58.915,80.4,136.2]"""

#Luz

"""taslock_times = [8.306, 7.777, 21.897, 44.313, 56.648, 91.013, 68.88]
ttaslock_times = [8.486, 12.174, 17.511, 31.783, 67.418, 44.652, 73.772]
backofflock_times = [4.908, 5.367, 6.477, 11.365, 6.883, 7.986, 10.408]
clhlock_times = [20.446, 27.99, 31.738, 35.565, 119.78, 203.737, 326.793]
mcslock_times = [21.345, 24.117, 29.487, 30.837, 117.491, 200.855, 326.452]
"""

#  cantidades de hilos
num_threads_list = [2, 3, 7, 15, 21, 30, 50]

#  DataFrame con los datos
data = {
    'Número de Hilos': num_threads_list,
    'TASLock': taslock_times,
    'TTASLock': ttaslock_times,
    'BackoffLock': backofflock_times,
    'CLHLock': clhlock_times,
    'MCSLock': mcslock_times
}

df = pd.DataFrame(data)

# Crear la tabla como figura de matplotlib 
fig, ax = plt.subplots(figsize=(12, 8))
ax.axis('tight')
ax.axis('off')
ax.table(cellText=df.values, colLabels=df.columns, cellLoc='center', loc='center')


plt.subplots_adjust(left=0.2, top=0.9)

# Guardar la tabla como una imagen 
plt.savefig('tabla_alta_resolucion.png', dpi=300, bbox_inches='tight', pad_inches=0.1)


plt.show()