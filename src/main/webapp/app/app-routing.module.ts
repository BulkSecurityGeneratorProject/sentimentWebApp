import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { twitterRouts } from 'app/twitter/twitter.routs';
import { redditRouts } from 'app/reddit/reddit.routs';

const LAYOUT_ROUTES = [navbarRoute, twitterRouts, redditRouts, ...errorRoute];

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                ...LAYOUT_ROUTES,
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#SentimentWebAppAdminModule'
                }
            ],
            { useHash: true, enableTracing: DEBUG_INFO_ENABLED }
        )
    ],
    exports: [RouterModule]
})
export class SentimentWebAppAppRoutingModule {}
