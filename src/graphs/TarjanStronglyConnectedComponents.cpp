#include <bits/stdc++.h>
using namespace std;
const char el = '\n';
const char sp = ' ';
typedef long long ll;

/* Example Input
5 6
1 2
2 3
3 1
3 4
4 5
5 4
 */

int main(){
    ios_base::sync_with_stdio(0);
    cin.tie(0); cout.tie(0);

    ll n,m; cin >> n >> m;
    vector<vector<ll>> adj(n);

    for(ll i=0;i<m;i++){
        ll u,v; cin >> u >> v; u--,v--;
        adj[u].push_back(v);
    }

    ll indices = 0;

    vector<ll> low_link(n,-1);
    vector<bool> on_stack(n);
    stack<ll> stack;

    function<void(ll)> dfs = [&] (ll u) -> void {
        ll current_index = indices++;
        low_link[u] = current_index;
        stack.push(u);
        on_stack[u] = true;

        for(auto v : adj[u]){
            if(low_link[v] == -1)
                dfs(v);
            if(on_stack[v])
                low_link[u] = min(low_link[u],low_link[v]);
        }

        if(low_link[u] == current_index) {
            while(true){
                ll top = stack.top();
                stack.pop();
                on_stack[u] = false;
                low_link[top] = current_index;
                if(top==u)
                    break;
            }
        }
    };


    for(int i=0;i<n;i++){
        if(low_link[i] == -1){
            dfs(i);
        }
    }

    for(ll x : low_link)
        cout << x << sp;

}
