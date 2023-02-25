#include <bits/stdc++.h>
using namespace std;
const char el = '\n';
const char sp = ' ';
typedef long long ll;

/* Example Input
4 3 5
1 2 5
1 3 9
2 3 3
1 2
2 1
1 3
1 4
3 2
 */

int main(){
    ios_base::sync_with_stdio(0);
    cin.tie(0); cout.tie(0);


    long long n,m,q; cin >> n >> m >> q;
    const long long INF = 1e17;
    vector<vector<long long>> adj_mat(n,vector<long long>(n,INF));

    for(long long i=0;i<m;i++){
        long long u,v,c; cin >> u >> v >> c; u--,v--;
        adj_mat[u][v] = min(adj_mat[u][v],c);
        adj_mat[v][u] = adj_mat[u][v]; // Example problem is bi-directional
    }

    for(long long k=0;k<n;k++){
        for(long long i=0;i<n;i++){
            for(long long j=0;j<n;j++){
                if(adj_mat[i][k] < INF && adj_mat[k][j] < INF)
                    adj_mat[i][j] = min(adj_mat[i][j],adj_mat[i][k] + adj_mat[k][j]);
            }
        }
    }

    while(q--){
        ll u,v; cin >> u >> v; u--,v--;
        ll ans = adj_mat[u][v];
        if(u==v)
            ans = 0;
        if(ans==INF)
            cout << -1 << el;
        else
            cout << ans << el;
    }
}