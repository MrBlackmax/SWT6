using AutoMapper;
using MediatR;
using Microsoft.EntityFrameworkCore;
using OrderManagement.Dal;
using OrderManagement.Dtos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Handlers.Queries
{
    internal class FindCustomerHandler : IRequestHandler<FindCustomersQuery, IEnumerable<CustomerDto>>
    {
        private readonly OrderManagementContext db;
        private readonly IMapper mapper;

        public FindCustomerHandler(OrderManagementContext db, IMapper mapper)
        {
            this.db = db;
            this.mapper = mapper;
        }

        public async Task<IEnumerable<CustomerDto>> Handle(FindCustomersQuery request, CancellationToken cancellationToken)
        {
            var customerQuery = db.Customers.AsNoTracking().Include(c => c.Address);

            if (request.rating == null)
            {
                var customers = await customerQuery.ToListAsync(cancellationToken: cancellationToken);
                return mapper.Map<IEnumerable<CustomerDto>>(customers);
            } else
            {
                var domainRating = mapper.Map<Domain.Rating>(request.rating);
                var customers = await customerQuery.Where(c => c.Rating == domainRating).ToListAsync(cancellationToken: cancellationToken);
                return mapper.Map<IEnumerable<CustomerDto>>(customers);
            }
        }
    }
}
