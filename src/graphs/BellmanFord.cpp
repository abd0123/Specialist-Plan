#include <bits/stdc++.h>
using namespace std;
const char el = '\n';
const char sp = ' ';
typedef long long ll;

class edge {
public:
    int u,v,c;
};

/* Example Input
5 5
0 1 3
1 2 4
2 3 5
3 1 -10
3 4 2
*/

int main(){

    int n,m; cin >> n >> m;
    vector<edge> edges;

    for(int i=0;i<m;i++){
        int u,v,c; cin >> u >> v >> c;
        edges.push_back({u,v,c});
    }

    int s = 0;

    const int INF = 1e9;
    vector<int> d(n,INF);
    vector<int> p(n);
    d[s] = 0;

    for(int i=0;i<n-1;i++){
        for(int j=0;j<m;j++){
            int u = edges[j].u;
            int v = edges[j].v;
            int c = edges[j].c;

            if(d[u] < INF){
                d[v] = min(d[v],d[u] + c);
                p[v] = u;
            }
        }
    }

    // Detecting negative cycles

    for(int j=0;j<m;j++){
        int u = edges[j].u;
        int v = edges[j].v;
        int c = edges[j].c;

        if(d[u] < INF){
            if(d[u] + c < d[v]){
                // Negative Cycle Detected
                // [u,v] are in the negative cycle
                p[v] = u;
            }
        }
    }
}